package elf.section.table

import elf.section.SectionHeader
import elf.section.content.Rel
import java.nio.ByteBuffer
import java.nio.ByteOrder

class RelTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseDataTable(byteBuffer, sectionHeader) {
    val rels: Array<Rel>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        rels = Array((sectionHeader.sh_size / sectionHeader.sh_entsize).toInt()) {
            Rel(buffer)
        }
    }

}