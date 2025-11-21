package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Hash(byteBuffer: ByteBuffer) : IOStream {
    val nbucket: Int
    val nchain: Int
    val buckets: Array<Int>
    val chains: Array<Int>

    init {
        nbucket = byteBuffer.int
        nchain = byteBuffer.int
        buckets = Array(nbucket) {
            byteBuffer.int
        }
        chains = Array(nchain) {
            byteBuffer.int
        }
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(buckets.size * 4 + chains.size * 4 + 2)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(buckets.size)
        byteBuffer.putInt(chains.size)
        buckets.forEach {
            byteBuffer.putInt(it)
        }
        chains.forEach {
            byteBuffer.putInt(it)
        }
        return byteBuffer.array()
    }

    override fun toString(): String {
        return "bucket[${buckets.size}] chain[${chains.size}]"
    }
}