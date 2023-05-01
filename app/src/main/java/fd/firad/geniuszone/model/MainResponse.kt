package fd.firad.geniuszone.model

data class MainResponse(
    val response_code: Int,
    val results: List<Result>
)