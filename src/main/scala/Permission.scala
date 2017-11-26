import Checker.check
import Enums.{ANY, NONE, Scope}

class Permission(scope: Scope, attributes: Option[List[String]] = None) {
  def any: Boolean = scope == ANY

  def own: Boolean = scope != NONE

  def context(own: Boolean, attributesToCheck: Option[List[String]] = None): Boolean = {
    val scopePermitted = if (own) scope != NONE else scope == ANY
    if (attributesToCheck.isDefined && attributes.isDefined)
      scopePermitted && check(attributesToCheck.get, attributes.get)
    else
      scopePermitted
  }
}

object Permission {
  def apply(scope: Scope, attributes: Option[List[String]] = None): Permission = new Permission(scope, attributes)

  val EmptyPermission = apply(NONE)
}