import scala.io.BufferedSource
import scala.io.Source

import io.circe.Decoder
import io.circe.Encoder
import io.circe.ParsingFailure
import io.circe.Json
import io.circe.parser
import io.circe.generic.semiauto.deriveDecoder
import io.circe.syntax._

// Use circe to parse the given spec data
val sourceFileJsonString: String = Source.fromFile("src/main/resources/data.json").mkString;
val parsedSpecJson: Json = parser.parse(sourceFileJsonString).getOrElse(Json.Null)

// instance of this type would become configuration parameters in the encoding and decoding process
case class Spec(ColumnNames: Vector[String], Offsets: Vector[String], FixedWidthEncoding: String, IncludeHeader: String, DelimitedEncoding: String)

given Decoder[Spec] = deriveDecoder[Spec]

parsedSpecJson.as[Spec]