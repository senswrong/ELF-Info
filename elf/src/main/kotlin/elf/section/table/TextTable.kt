package elf.section.table

import elf.section.SectionHeader
import java.nio.ByteBuffer

class TextTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseDataTable(byteBuffer, sectionHeader) {
}