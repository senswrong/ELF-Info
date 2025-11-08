package elf.section.table

import elf.section.SectionHeader
import java.nio.ByteBuffer

class BssTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseTable()