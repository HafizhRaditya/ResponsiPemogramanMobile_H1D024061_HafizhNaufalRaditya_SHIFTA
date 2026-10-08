package com.pemmob1.h1d024061.responsi.data.model

data class DigimonListResponse(
    val content: List<Digimon>?,
)

data class Digimon(
    val id: Int,
    val name: String,
    val image: String?,
)

data class DigimonDetail(
    val id: Int,
    val name: String,
    val xAntibody: Boolean?,
    val releaseDate: String?,
    val images: List<DigimonImage>?,
    val levels: List<DigimonLevel>?,
    val types: List<DigimonType>?,
    val attributes: List<DigimonAttribute>?,
    val fields: List<DigimonField>?,
    val descriptions: List<DigimonDescription>?,
) {
    val imageUrl: String?
        get() = images?.firstOrNull()?.href

    val levelText: String
        get() = levels?.mapNotNull { it.level }.toDisplayText()

    val typeText: String
        get() = types?.mapNotNull { it.type }.toDisplayText()

    val attributeText: String
        get() = attributes?.mapNotNull { it.attribute }.toDisplayText()

    val fieldText: String
        get() = fields?.mapNotNull { it.field }.toDisplayText()

    val description: String?
        get() = descriptions
            ?.firstOrNull { it.language == "en_us" }
            ?.description
            ?.takeIf { it.isNotBlank() }
}

data class DigimonImage(
    val href: String?,
)

data class DigimonLevel(
    val level: String?,
)

data class DigimonType(
    val type: String?,
)

data class DigimonAttribute(
    val attribute: String?,
)

data class DigimonField(
    val field: String?,
)

data class DigimonDescription(
    val language: String?,
    val description: String?,
)

private fun List<String>?.toDisplayText(): String =
    this?.filter { it.isNotBlank() }
        ?.joinToString(separator = ", ")
        .orEmpty()
        .ifEmpty { "Tidak diketahui" }
