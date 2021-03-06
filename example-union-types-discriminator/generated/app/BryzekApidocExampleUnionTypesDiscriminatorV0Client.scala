/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.1.41
 * apidoc:0.9.51 http://localhost:9000/bryzek/apidoc-example-union-types-discriminator/0.1.41/play_2_4_client
 */
package com.bryzek.apidoc.example.union.types.discriminator.v0.models {

  sealed trait User

  case class GuestUser(
    id: String,
    email: _root_.scala.Option[String] = None
  ) extends User

  case class RegisteredUser(
    id: String,
    email: String
  ) extends User

  /**
   * Provides future compatibility in clients - in the future, when a type is added
   * to the union User, it will need to be handled in the client code. This
   * implementation will deserialize these future types as an instance of this class.
   */
  case class UserUndefinedType(
    description: String
  ) extends User


  /**
   * Wrapper class to support the union types containing the datatype[string]
   */
  case class UserString(
    value: String
  ) extends User

  sealed trait SystemUser extends User

  object SystemUser {

    case object System extends SystemUser { override def toString = "system" }
    case object Anonymous extends SystemUser { override def toString = "anonymous" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends SystemUser

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(System, Anonymous)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): SystemUser = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[SystemUser] = byName.get(value.toLowerCase)

  }

}

package com.bryzek.apidoc.example.union.types.discriminator.v0.models {

  package object json {
    import play.api.libs.json.__
    import play.api.libs.json.JsString
    import play.api.libs.json.Writes
    import play.api.libs.functional.syntax._
    import com.bryzek.apidoc.example.union.types.discriminator.v0.models.json._

    private[v0] implicit val jsonReadsUUID = __.read[String].map(java.util.UUID.fromString)

    private[v0] implicit val jsonWritesUUID = new Writes[java.util.UUID] {
      def writes(x: java.util.UUID) = JsString(x.toString)
    }

    private[v0] implicit val jsonReadsJodaDateTime = __.read[String].map { str =>
      import org.joda.time.format.ISODateTimeFormat.dateTimeParser
      dateTimeParser.parseDateTime(str)
    }

    private[v0] implicit val jsonWritesJodaDateTime = new Writes[org.joda.time.DateTime] {
      def writes(x: org.joda.time.DateTime) = {
        import org.joda.time.format.ISODateTimeFormat.dateTime
        val str = dateTime.print(x)
        JsString(str)
      }
    }

    implicit val jsonReadsApidocExampleUnionTypesDiscriminatorSystemUser = new play.api.libs.json.Reads[com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser(v))
              case err: play.api.libs.json.JsError => err
            }
          }
        }
      }
    }

    implicit def jsonReadsApidocExampleUnionTypesDiscriminatorGuestUser: play.api.libs.json.Reads[GuestUser] = {
      (
        (__ \ "id").read[String] and
        (__ \ "email").readNullable[String]
      )(GuestUser.apply _)
    }

    implicit def jsonWritesApidocExampleUnionTypesDiscriminatorGuestUser: play.api.libs.json.Writes[GuestUser] = {
      (
        (__ \ "id").write[String] and
        (__ \ "email").writeNullable[String]
      )(unlift(GuestUser.unapply _))
    }

    implicit def jsonReadsApidocExampleUnionTypesDiscriminatorRegisteredUser: play.api.libs.json.Reads[RegisteredUser] = {
      (
        (__ \ "id").read[String] and
        (__ \ "email").read[String]
      )(RegisteredUser.apply _)
    }

    implicit def jsonWritesApidocExampleUnionTypesDiscriminatorRegisteredUser: play.api.libs.json.Writes[RegisteredUser] = {
      (
        (__ \ "id").write[String] and
        (__ \ "email").write[String]
      )(unlift(RegisteredUser.unapply _))
    }

    implicit def jsonReadsApidocExampleUnionTypesDiscriminatorUserString: play.api.libs.json.Reads[UserString] = {
      (__ \ "value").read[String].map { x => new UserString(value = x) }
    }

    implicit def jsonWritesApidocExampleUnionTypesDiscriminatorUserString: play.api.libs.json.Writes[UserString] = new play.api.libs.json.Writes[UserString] {
      def writes(x: UserString) = play.api.libs.json.Json.obj(
        "value" -> play.api.libs.json.Json.toJson(x.value)
      )
    }

    implicit def jsonReadsApidocExampleUnionTypesDiscriminatorUser: play.api.libs.json.Reads[User] = new play.api.libs.json.Reads[User] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[User] = {
        (js \ "discriminator").validate[String] match {
          case play.api.libs.json.JsError(msg) => play.api.libs.json.JsError(msg)
          case play.api.libs.json.JsSuccess(discriminator, _) => {
            discriminator match {
              case "registered_user" => js.validate[com.bryzek.apidoc.example.union.types.discriminator.v0.models.RegisteredUser]
              case "guest_user" => js.validate[com.bryzek.apidoc.example.union.types.discriminator.v0.models.GuestUser]
              case "system_user" => js.validate[com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser]
              case "string" => js.validate[UserString]
              case other => play.api.libs.json.JsSuccess(com.bryzek.apidoc.example.union.types.discriminator.v0.models.UserUndefinedType(other))
            }
          }
        }
      }
    }

    implicit def jsonWritesApidocExampleUnionTypesDiscriminatorUser: play.api.libs.json.Writes[User] = new play.api.libs.json.Writes[User] {
      def writes(obj: com.bryzek.apidoc.example.union.types.discriminator.v0.models.User) = {
        obj match {
          case x: com.bryzek.apidoc.example.union.types.discriminator.v0.models.RegisteredUser => play.api.libs.json.Json.obj(
            "discriminator" -> "registered_user",
            "id" -> play.api.libs.json.Json.toJson(x.id),
            "email" -> play.api.libs.json.Json.toJson(x.email)
          )
          case x: com.bryzek.apidoc.example.union.types.discriminator.v0.models.GuestUser => play.api.libs.json.Json.obj(
            "discriminator" -> "guest_user",
            "id" -> play.api.libs.json.Json.toJson(x.id),
            "email" -> play.api.libs.json.Json.toJson(x.email)
          )
          case x: com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser => play.api.libs.json.Json.obj(
            "discriminator" -> "system_user",
            "value" -> play.api.libs.json.JsString(x.toString)
          )
          case x: UserString => play.api.libs.json.Json.obj(
            "discriminator" -> "string",
            "value" -> play.api.libs.json.Json.toJson(x.value)
          )
          case x: com.bryzek.apidoc.example.union.types.discriminator.v0.models.UserUndefinedType => {
            sys.error("The type[com.bryzek.apidoc.example.union.types.discriminator.v0.models.UserUndefinedType] should never be serialized")
          }
        }
      }
    }
  }
}

package com.bryzek.apidoc.example.union.types.discriminator.v0 {

  object Bindables {

    import play.api.mvc.{PathBindable, QueryStringBindable}
    import org.joda.time.{DateTime, LocalDate}
    import org.joda.time.format.ISODateTimeFormat
    import com.bryzek.apidoc.example.union.types.discriminator.v0.models._

    // Type: date-time-iso8601
    implicit val pathBindableTypeDateTimeIso8601 = new PathBindable.Parsing[org.joda.time.DateTime](
      ISODateTimeFormat.dateTimeParser.parseDateTime(_), _.toString, (key: String, e: Exception) => s"Error parsing date time $key. Example: 2014-04-29T11:56:52Z"
    )

    implicit val queryStringBindableTypeDateTimeIso8601 = new QueryStringBindable.Parsing[org.joda.time.DateTime](
      ISODateTimeFormat.dateTimeParser.parseDateTime(_), _.toString, (key: String, e: Exception) => s"Error parsing date time $key. Example: 2014-04-29T11:56:52Z"
    )

    // Type: date-iso8601
    implicit val pathBindableTypeDateIso8601 = new PathBindable.Parsing[org.joda.time.LocalDate](
      ISODateTimeFormat.yearMonthDay.parseLocalDate(_), _.toString, (key: String, e: Exception) => s"Error parsing date $key. Example: 2014-04-29"
    )

    implicit val queryStringBindableTypeDateIso8601 = new QueryStringBindable.Parsing[org.joda.time.LocalDate](
      ISODateTimeFormat.yearMonthDay.parseLocalDate(_), _.toString, (key: String, e: Exception) => s"Error parsing date $key. Example: 2014-04-29"
    )

    // Enum: SystemUser
    private[this] val enumSystemUserNotFound = (key: String, e: Exception) => s"Unrecognized $key, should be one of ${com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser.all.mkString(", ")}"

    implicit val pathBindableEnumSystemUser = new PathBindable.Parsing[com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser] (
      SystemUser.fromString(_).get, _.toString, enumSystemUserNotFound
    )

    implicit val queryStringBindableEnumSystemUser = new QueryStringBindable.Parsing[com.bryzek.apidoc.example.union.types.discriminator.v0.models.SystemUser](
      SystemUser.fromString(_).get, _.toString, enumSystemUserNotFound
    )

  }

}


package com.bryzek.apidoc.example.union.types.discriminator.v0 {

  object Constants {

    val UserAgent = "apidoc:0.9.51 http://localhost:9000/bryzek/apidoc-example-union-types-discriminator/0.1.41/play_2_4_client"
    val Version = "0.1.41"
    val VersionMajor = 0

  }

  class Client(
    apiUrl: String,
    auth: scala.Option[com.bryzek.apidoc.example.union.types.discriminator.v0.Authorization] = None,
    defaultHeaders: Seq[(String, String)] = Nil
  ) {
    import com.bryzek.apidoc.example.union.types.discriminator.v0.models.json._

    private[this] val logger = play.api.Logger("com.bryzek.apidoc.example.union.types.discriminator.v0.Client")

    logger.info(s"Initializing com.bryzek.apidoc.example.union.types.discriminator.v0.Client for url $apiUrl")

    def users: Users = Users

    object Users extends Users {
      override def get()(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Seq[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User]] = {
        _executeRequest("GET", s"/users").map {
          case r if r.status == 200 => _root_.com.bryzek.apidoc.example.union.types.discriminator.v0.Client.parseJson("Seq[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User]", r, _.validate[Seq[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User]])
          case r => throw new com.bryzek.apidoc.example.union.types.discriminator.v0.errors.FailedRequest(r.status, s"Unsupported response code[${r.status}]. Expected: 200")
        }
      }

      override def getById(
        id: String
      )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User] = {
        _executeRequest("GET", s"/users/${play.utils.UriEncoding.encodePathSegment(id, "UTF-8")}").map {
          case r if r.status == 200 => _root_.com.bryzek.apidoc.example.union.types.discriminator.v0.Client.parseJson("com.bryzek.apidoc.example.union.types.discriminator.v0.models.User", r, _.validate[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User])
          case r if r.status == 404 => throw new com.bryzek.apidoc.example.union.types.discriminator.v0.errors.UnitResponse(r.status)
          case r => throw new com.bryzek.apidoc.example.union.types.discriminator.v0.errors.FailedRequest(r.status, s"Unsupported response code[${r.status}]. Expected: 200, 404")
        }
      }

      override def post(
        user: com.bryzek.apidoc.example.union.types.discriminator.v0.models.User
      )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User] = {
        val payload = play.api.libs.json.Json.toJson(user)

        _executeRequest("POST", s"/users", body = Some(payload)).map {
          case r if r.status == 201 => _root_.com.bryzek.apidoc.example.union.types.discriminator.v0.Client.parseJson("com.bryzek.apidoc.example.union.types.discriminator.v0.models.User", r, _.validate[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User])
          case r => throw new com.bryzek.apidoc.example.union.types.discriminator.v0.errors.FailedRequest(r.status, s"Unsupported response code[${r.status}]. Expected: 201")
        }
      }
    }

    def _requestHolder(path: String): play.api.libs.ws.WSRequest = {
      import play.api.Play.current

      val holder = play.api.libs.ws.WS.url(apiUrl + path).withHeaders(
        "User-Agent" -> Constants.UserAgent,
        "X-Apidoc-Version" -> Constants.Version,
        "X-Apidoc-Version-Major" -> Constants.VersionMajor.toString
      ).withHeaders(defaultHeaders : _*)
      auth.fold(holder) {
        case Authorization.Basic(username, password) => {
          holder.withAuth(username, password.getOrElse(""), play.api.libs.ws.WSAuthScheme.BASIC)
        }
        case a => sys.error("Invalid authorization scheme[" + a.getClass + "]")
      }
    }

    def _logRequest(method: String, req: play.api.libs.ws.WSRequest)(implicit ec: scala.concurrent.ExecutionContext): play.api.libs.ws.WSRequest = {
      val queryComponents = for {
        (name, values) <- req.queryString
        value <- values
      } yield s"$name=$value"
      val url = s"${req.url}${queryComponents.mkString("?", "&", "")}"
      auth.fold(logger.info(s"curl -X $method $url")) { _ =>
        logger.info(s"curl -X $method -u '[REDACTED]:' $url")
      }
      req
    }

    def _executeRequest(
      method: String,
      path: String,
      queryParameters: Seq[(String, String)] = Seq.empty,
      body: Option[play.api.libs.json.JsValue] = None
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[play.api.libs.ws.WSResponse] = {
      method.toUpperCase match {
        case "GET" => {
          _logRequest("GET", _requestHolder(path).withQueryString(queryParameters:_*)).get()
        }
        case "POST" => {
          _logRequest("POST", _requestHolder(path).withQueryString(queryParameters:_*).withHeaders("Content-Type" -> "application/json; charset=UTF-8")).post(body.getOrElse(play.api.libs.json.Json.obj()))
        }
        case "PUT" => {
          _logRequest("PUT", _requestHolder(path).withQueryString(queryParameters:_*).withHeaders("Content-Type" -> "application/json; charset=UTF-8")).put(body.getOrElse(play.api.libs.json.Json.obj()))
        }
        case "PATCH" => {
          _logRequest("PATCH", _requestHolder(path).withQueryString(queryParameters:_*)).patch(body.getOrElse(play.api.libs.json.Json.obj()))
        }
        case "DELETE" => {
          _logRequest("DELETE", _requestHolder(path).withQueryString(queryParameters:_*)).delete()
        }
         case "HEAD" => {
          _logRequest("HEAD", _requestHolder(path).withQueryString(queryParameters:_*)).head()
        }
         case "OPTIONS" => {
          _logRequest("OPTIONS", _requestHolder(path).withQueryString(queryParameters:_*)).options()
        }
        case _ => {
          _logRequest(method, _requestHolder(path).withQueryString(queryParameters:_*))
          sys.error("Unsupported method[%s]".format(method))
        }
      }
    }

  }

  object Client {

    def parseJson[T](
      className: String,
      r: play.api.libs.ws.WSResponse,
      f: (play.api.libs.json.JsValue => play.api.libs.json.JsResult[T])
    ): T = {
      f(play.api.libs.json.Json.parse(r.body)) match {
        case play.api.libs.json.JsSuccess(x, _) => x
        case play.api.libs.json.JsError(errors) => {
          throw new com.bryzek.apidoc.example.union.types.discriminator.v0.errors.FailedRequest(r.status, s"Invalid json for class[" + className + "]: " + errors.mkString(" "))
        }
      }
    }

  }

  sealed trait Authorization
  object Authorization {
    case class Basic(username: String, password: Option[String] = None) extends Authorization
  }

  trait Users {
    def get()(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[Seq[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User]]

    def getById(
      id: String
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User]

    def post(
      user: com.bryzek.apidoc.example.union.types.discriminator.v0.models.User
    )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[com.bryzek.apidoc.example.union.types.discriminator.v0.models.User]
  }

  package errors {

    import com.bryzek.apidoc.example.union.types.discriminator.v0.models.json._

    case class UnitResponse(status: Int) extends Exception(s"HTTP $status")

    case class FailedRequest(responseCode: Int, message: String, requestUri: Option[_root_.java.net.URI] = None) extends Exception(s"HTTP $responseCode: $message")

  }

}