package core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ListSerializer: KSerializer<List<String>> {
    override val descriptor: SerialDescriptor
        get() = ListSerializer(String.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: List<String>) {
        encoder.encodeSerializableValue(ListSerializer(String.serializer()), value)
    }

    override fun deserialize(decoder: Decoder): List<String> {
        return decoder.decodeSerializableValue(ListSerializer(String.serializer()))
    }

}
object ListSetSerializer: KSerializer<MutableSet<String>> {
    override val descriptor: SerialDescriptor
        get() = ListSerializer(String.serializer()).descriptor

    override fun deserialize(decoder: Decoder): MutableSet<String> {
        return decoder.decodeSerializableValue(SetSerializer(String.serializer())).toMutableSet()
    }
    override fun serialize(encoder: Encoder, value: MutableSet<String>) {
        encoder.encodeSerializableValue(SetSerializer(String.serializer()), value)
    }

}