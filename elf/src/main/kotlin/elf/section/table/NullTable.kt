package elf.section.table

import elf.section.SectionHeader
import java.nio.ByteBuffer

class NullTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseDataTable(byteBuffer, sectionHeader)