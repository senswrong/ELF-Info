package elf.section.table

import elf.section.SectionHeader
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TextTable(override val name: String, byteBuffer: ByteBuffer, sectionHeader: SectionHeader) :
    BaseDataTable(name, byteBuffer, sectionHeader) {
    //todo
    val armCode: Array<Long>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        armCode = Array((sectionHeader.sh_size / 8).toInt()) {
            buffer.getLong()
        }
    }
}