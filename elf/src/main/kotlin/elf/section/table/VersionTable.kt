package elf.section.table

import elf.section.SectionHeader
import ex.byte
import java.nio.ByteBuffer
import java.nio.ByteOrder

class VersionTable(override val name: String, byteBuffer: ByteBuffer, sectionHeader: SectionHeader) :
    BaseDataTable(name, byteBuffer, sectionHeader) {
    val versions: Array<Byte>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        versions = Array((sectionHeader.sh_size / 2).toInt()) {
            buffer.byte
        }
    }
}