package elf.section.content

import base.IOStream
import elf.section.table.StringTable
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

class Note(byteBuffer: ByteBuffer) : IOStream {
    val n_namesz: Int
    val n_descsz: Int
    val n_type: Int
    val name: String = "GUN"
    var buildId: ByteArray

    init {
        n_namesz = byteBuffer.int
        n_descsz = byteBuffer.int
        n_type = byteBuffer.int
        byteBuffer.int
        buildId = ByteArray(20)
        byteBuffer.get(buildId)
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(36)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(n_namesz)
        byteBuffer.putInt(n_descsz)
        byteBuffer.putInt(n_type)
        byteBuffer.put(name.toByteArray(StandardCharsets.UTF_8))
        byteBuffer.put(0)
        byteBuffer.put(buildId)
        return byteBuffer.array()
    }

    fun toString(dynString: StringTable?): String {
        return "${n_namesz.toString(16)}\t${n_descsz.toString(16)}\t${n_type.toString(16)}}"
    }
}