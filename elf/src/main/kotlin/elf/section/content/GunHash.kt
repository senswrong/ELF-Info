package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class GunHash(byteBuffer: ByteBuffer) : IOStream {
    /*桶数组的长度（桶的数量，通常是 2 的幂，如 16、32、64）*/
    var nbucket: Int

    /*符号表（.dynsym/.symtab）中「首个参与哈希的符号索引」（跳过未定义符号）*/
    var symndx: Int

    /*位图（Bloom Filter）的大小（单位：Elf32_Word/Elf64_Word 元素个数）*/
    var maskwords: Int

    /*位图的移位系数（用于快速计算符号对应的位图位，过滤无效查找）*/
    var shift2: Int

    var buckets: Array<Long>
    var masks: Array<Int>
    var hashValues: Array<Long>

    init {
        nbucket = byteBuffer.int
        symndx = byteBuffer.int
        maskwords = byteBuffer.int
        shift2 = byteBuffer.int

        buckets = Array(nbucket) {
            byteBuffer.long
        }
        masks = Array(maskwords) {
            byteBuffer.int
        }

        hashValues = Array((byteBuffer.capacity() - byteBuffer.position()) / 8) {
            byteBuffer.long
        }
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(16 + buckets.size * 8 + masks.size * 4 + hashValues.size * 8)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(buckets.size)
        byteBuffer.putInt(symndx)
        byteBuffer.putInt(masks.size)
        byteBuffer.putInt(shift2)
        buckets.forEach {
            byteBuffer.putLong(it)
        }
        masks.forEach {
            byteBuffer.putInt(it)
        }
        hashValues.forEach {
            byteBuffer.putLong(it)
        }
        return byteBuffer.array()
    }

    override fun toString(): String {
        return "GunHash(shift2=$shift2, maskwords=$maskwords, symndx=$symndx, nbucket=$nbucket, buckets=${buckets.contentToString()}, masks=${masks.contentToString()}, hashValues=${hashValues.contentToString()})"
    }

}