package elf.section.table

import elf.section.SectionHeader
import elf.section.content.Rela
import java.nio.ByteBuffer
import java.nio.ByteOrder

class RelaTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseDataTable(byteBuffer, sectionHeader) {
    val relas: Array<Rela>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        relas = Array((sectionHeader.sh_size / sectionHeader.sh_entsize).toInt()) {
            Rela(buffer)
        }
    }

}