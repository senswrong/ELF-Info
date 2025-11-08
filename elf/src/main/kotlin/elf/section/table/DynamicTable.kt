package elf.section.table

import elf.section.SectionHeader
import elf.section.content.Dynamic
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DynamicTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseDataTable(byteBuffer, sectionHeader) {
    val dynamics: Array<Dynamic>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        dynamics = Array((sectionHeader.sh_size / sectionHeader.sh_entsize).toInt()) {
            Dynamic(buffer)
        }
    }
}