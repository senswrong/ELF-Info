package elf.section.table

import elf.ElfFile
import elf.ElfFile.Companion.readInfo
import elf.section.SectionHeader
import ex.seek
import java.nio.ByteBuffer

open class BaseDataTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseTable() {
    var data: ByteArray

    val start: Int
    val end: Int

    init {
        byteBuffer.seek(sectionHeader.sh_offset.toInt())
        start = byteBuffer.position()
        data = ByteArray(sectionHeader.sh_size.toInt())
        byteBuffer.get(data)
        ElfFile.readCount += data.size
        end = byteBuffer.position()
        readInfo += Triple(
            start,
            sectionHeader.sh_name,
            "\t | ${this::class.java.simpleName} \t [${start.toString(16)} ~ ${end.toString(16)}]"
        )
    }

    override fun toBytes(): ByteArray {
        return data
    }
}