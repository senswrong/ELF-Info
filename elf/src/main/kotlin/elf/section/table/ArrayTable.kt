package elf.section.table

import elf.section.SectionHeader
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ArrayTable(override val name: String, byteBuffer: ByteBuffer, sectionHeader: SectionHeader) :
    BaseDataTable(name, byteBuffer, sectionHeader) {
    val funcs: Array<Long>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        funcs = Array((sectionHeader.sh_size / 8).toInt()) {
            buffer.getLong()
        }
    }
}