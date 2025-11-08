package ex

import java.nio.ByteBuffer

var seekInfo = ""
fun ByteBuffer.seek(position: Int) {
    if (position() != position)
        seekInfo += ("${position()}<>${position} -> ${position-position()}\n")
    position(position)
}

val ByteBuffer.byte: Byte
    get() = this.get()

val ByteBuffer.string: String
    get() {
        var byte: Byte
        val bytes = mutableListOf<Byte>()
        do {
            byte = get()
            bytes += byte
        } while (byte.toInt() != 0)

        if (bytes.size == 1 && bytes[0].toInt() == 0) {
            return ""
        }
        return String(bytes.subList(0, bytes.size - 1).toByteArray())
    }
