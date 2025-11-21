package elf

import base.IOStream
import elf.program.ProgramHeader
import elf.section.SectionHeader
import elf.section.SectionTypes
import elf.section.table.*
import ex.seek
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfFile(datas: ByteArray) : IOStream {
    val header: ElfHeader
    val programHeaders: Array<ProgramHeader>
    val sectionStringHeader: SectionHeader
    val sectionHeaders: Array<SectionHeader>

    companion object {
        var readCount = 0L
        var readInfo = mutableListOf<Triple<Int, Int, String>>()
    }

    init {
        val byteBuffer = ByteBuffer.wrap(datas)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        header = ElfHeader(byteBuffer)
        programHeaders = Array(header.e_phnum.toInt()) {
            byteBuffer.seek(header.e_phoff.toInt() + it * header.e_phentsize)
            ProgramHeader(byteBuffer)
        }

        byteBuffer.seek(header.e_shoff.toInt() + header.e_shstrndx.toInt() * header.e_shentsize)
        sectionStringHeader = SectionHeader(byteBuffer)
        sectionStringHeader.table = StringTable(".shstrtab", byteBuffer, sectionStringHeader)
        sectionHeaders = Array(header.e_shnum.toInt()) {
            if (it == header.e_shstrndx.toInt())
                sectionStringHeader else {
                byteBuffer.seek(header.e_shoff.toInt() + it * header.e_shentsize)
                SectionHeader(byteBuffer)
            }
        }
        sectionHeaders.forEachIndexed { index, sectionHeader ->
            val sectionStringTable = (sectionStringHeader.table as StringTable)
            sectionHeader.name = sectionStringTable.getText(sectionHeader.sh_name)
            if (sectionHeader == sectionStringHeader) return@forEachIndexed
            sectionHeader.table = when (sectionHeader.sh_type) {
                SectionTypes.NOTE -> NoteTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.REL -> RelTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.RELA -> RelaTable(sectionHeader.name, byteBuffer, sectionHeader)
                //Arm指令
                SectionTypes.PROGBITS -> TextTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.DYNAMIC -> DynamicTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.STRTAB -> StringTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.HASH -> HashTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.GNU_HASH -> GunHashTable(sectionHeader.name, byteBuffer, sectionHeader)
                SectionTypes.DYNSYM,
                SectionTypes.SYMTAB -> SymTable(sectionHeader.name, byteBuffer, sectionHeader)

                SectionTypes.INIT_ARRAY,
                SectionTypes.FINI_ARRAY,
                SectionTypes.PREINIT_ARRAY -> ArrayTable(sectionHeader.name, byteBuffer, sectionHeader)

                SectionTypes.GNU_VERDEF,
                SectionTypes.GNU_VERDNEED,
                SectionTypes.GNY_VERSYM -> VersionTable(sectionHeader.name, byteBuffer, sectionHeader)

                SectionTypes.NULL,
                SectionTypes.NOBITS -> BaseTable(sectionHeader.name)

                else -> BaseDataTable(sectionHeader.name, byteBuffer, sectionHeader)
            }
        }
//        println("ELF---> ${datas.size} - $readCount = ${datas.size - readCount}")
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(0)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return header.toString() + "\n----------\n" + programHeaders.contentToString() + "\n----------\n" + sectionHeaders.contentToString()
    }
}