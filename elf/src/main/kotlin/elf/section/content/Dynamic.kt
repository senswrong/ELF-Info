package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Dynamic(byteBuffer: ByteBuffer) : IOStream {
    val d_tag: Long        /* Symbol value */
    val d_val: Long        /* Symbol size */
    val d_ptr: Long        /* Symbol size */

    init {
        d_tag = byteBuffer.long
        d_val = byteBuffer.long
        d_ptr = d_val
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(16)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putLong(d_tag)
        byteBuffer.putLong(d_val)
        return byteBuffer.array()
    }
}