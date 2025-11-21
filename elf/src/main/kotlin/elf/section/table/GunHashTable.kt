package elf.section.table

import elf.section.SectionHeader
import elf.section.content.GunHash
import java.nio.ByteBuffer
import java.nio.ByteOrder

class GunHashTable(override val name: String, byteBuffer: ByteBuffer, sectionHeader: SectionHeader) :
    BaseDataTable(name, byteBuffer, sectionHeader) {
    val gunHash: GunHash

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        gunHash = GunHash(buffer)
    }
}