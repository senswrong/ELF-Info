package elf.section.table

import elf.section.SectionHeader
import elf.section.content.Hash
import java.nio.ByteBuffer
import java.nio.ByteOrder

class HashTable(override val name: String, byteBuffer: ByteBuffer, sectionHeader: SectionHeader) :
    BaseDataTable(name, byteBuffer, sectionHeader) {
    val hash: Hash

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        hash = Hash(buffer)
    }
}