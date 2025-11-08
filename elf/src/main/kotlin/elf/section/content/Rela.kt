package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Rela(byteBuffer: ByteBuffer) : IOStream {
    val r_offset: Long
    val r_info: Long
    val r_addend: Long

    init {
        r_offset = byteBuffer.long
        r_info = byteBuffer.long
        r_addend = byteBuffer.long
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(24)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putLong(r_offset)
        byteBuffer.putLong(r_info)
        byteBuffer.putLong(r_addend)
        return byteBuffer.array()
    }
}