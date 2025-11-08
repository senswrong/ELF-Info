package elf.section.content

import base.IOStream
import ex.byte
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Symbol(byteBuffer: ByteBuffer) : IOStream {
    val st_name: Int /* Symbol name (string tbl index) */
    val st_info: Byte        /* Symbol type and binding */
    val st_other: Byte        /* Symbol visibility */
    val st_shndx: Short        /* Section index */
    val st_value: Long        /* Symbol value */
    val st_size: Long        /* Symbol size */

    init {
        st_name = byteBuffer.int
        st_info = byteBuffer.byte
        st_other = byteBuffer.byte
        st_shndx = byteBuffer.short
        st_value = byteBuffer.long
        st_size = byteBuffer.long
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(24)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(st_name)
        byteBuffer.put(st_info)
        byteBuffer.put(st_other)
        byteBuffer.putShort(st_shndx)
        byteBuffer.putLong(st_value)
        byteBuffer.putLong(st_size)
        return byteBuffer.array()
    }
}