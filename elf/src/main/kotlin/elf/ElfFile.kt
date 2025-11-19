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
    val sectionHeaders: Array<SectionHeader>
    val stringTable: StringTable
    val tables: Array<BaseTable>

    companion object {
        var readCount = 0L
        var readInfo = mutableListOf<Triple<Int, Int, String>>()
    }

    init {
//        val available = datas.size
        val byteBuffer = ByteBuffer.wrap(datas)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        header = ElfHeader(byteBuffer)
        programHeaders = Array(header.e_phnum.toInt()) {
            byteBuffer.seek(header.e_phoff.toInt() + it * header.e_phentsize)
            ProgramHeader(byteBuffer)
        }

        sectionHeaders = Array(header.e_shnum.toInt()) {
            byteBuffer.seek(header.e_shoff.toInt() + it * header.e_ehsize)
            SectionHeader(byteBuffer)
        }
        tables = Array(sectionHeaders.size) {
            val sectionHeader = sectionHeaders[it]
            when (sectionHeader.sh_type) {
                SectionTypes.NOTE -> NoteTable(byteBuffer, sectionHeader)
                SectionTypes.REL -> RelTable(byteBuffer, sectionHeader)
                SectionTypes.RELA -> RelaTable(byteBuffer, sectionHeader)
                SectionTypes.DYNSYM,
                SectionTypes.SYMTAB -> SymTable(byteBuffer, sectionHeader)

                SectionTypes.NULL -> NullTable(byteBuffer, sectionHeader)
                SectionTypes.PROGBITS -> TextTable(byteBuffer, sectionHeader)
                SectionTypes.DYNAMIC -> DynamicTable(byteBuffer, sectionHeader)
                SectionTypes.STRTAB -> StringTable(byteBuffer, sectionHeader)
                SectionTypes.NOBITS -> BaseTable()
                else -> BaseDataTable(
                    byteBuffer,
                    sectionHeader
                )
            }
        }
        stringTable = tables[header.e_shstrndx.toInt()] as StringTable
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