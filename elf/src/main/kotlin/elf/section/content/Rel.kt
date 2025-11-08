package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Rel(byteBuffer: ByteBuffer) : IOStream {
    val r_offset: Long
    val r_info: Long

    init {
        r_offset = byteBuffer.long
        r_info = byteBuffer.long
    }
    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(16)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putLong(r_offset)
        byteBuffer.putLong(r_info)
        return byteBuffer.array()
    }
}