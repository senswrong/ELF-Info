package elf.section.table

import elf.section.SectionHeader
import elf.section.content.Symbol
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SymTable(override val name: String, byteBuffer: ByteBuffer, sectionHeader: SectionHeader) :
    BaseDataTable(name, byteBuffer, sectionHeader) {
    val symbols: Array<Symbol>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        symbols = Array((sectionHeader.sh_size / sectionHeader.sh_entsize).toInt()) {
            Symbol(buffer)
        }
    }
}