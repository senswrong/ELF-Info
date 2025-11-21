package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

class Note(byteBuffer: ByteBuffer) : IOStream {
    val n_namesz: Int
    val n_descsz: Int
    val n_type: Int
    val name: String
    var description: ByteArray

    init {
        n_namesz = byteBuffer.int
        n_descsz = byteBuffer.int
        n_type = byteBuffer.int
        val nameData  = ByteArray(n_namesz)
        byteBuffer.get(nameData)
        name = nameData.toString(StandardCharsets.UTF_8)
        description = ByteArray(n_descsz)
        byteBuffer.get(description)
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(36)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(n_namesz)
        byteBuffer.putInt(n_descsz)
        byteBuffer.putInt(n_type)
        byteBuffer.put(name.toByteArray(StandardCharsets.UTF_8))
        byteBuffer.put(0)
        byteBuffer.put(description)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return "${n_namesz.toString(16)}\t${n_descsz.toString(16)}\t${n_type.toString(16)}}"
    }
}