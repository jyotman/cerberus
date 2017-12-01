import Enums.{ANY, OWN}

import scala.collection.immutable.HashMap

object Dsl {

  val Attribute_Splitter = "SPLITTER"

  implicit class AssignResourceToRole(role: String) {
    def can(resourcesWithActions: Map[String, Map[String, Permission]]): Map[String, Map[String, Map[String, Permission]]] = HashMap(role -> resourcesWithActions)
  }

  implicit class JoinResourceAndActionAnyPermission(action: String) {
    def any(resource: String): Map[String, Map[String, Permission]] = HashMap(resource -> HashMap(action -> Permission(ANY)))
  }

  implicit class JoinResourceAndActionOwnPermission(action: String) {
    def own(resource: String): Map[String, Map[String, Permission]] = HashMap(resource -> HashMap(action -> Permission(OWN)))
  }

  implicit class JoinResource(resourceWithActions: Map[String, Map[String, Permission]]) {
    def also(resourceWithActions2: Map[String, Map[String, Permission]]): Map[String, Map[String, Permission]] = {
      val resource = resourceWithActions2.head._1
      if (resourceWithActions.contains(resource))
        resourceWithActions.updated(resource, resourceWithActions2.head._2 ++ resourceWithActions(resource))
      else
        resourceWithActions ++ resourceWithActions2
    }
  }

  implicit class JoinRole(role: Map[String, Map[String, Map[String, Permission]]]) {
    def and(role2: Map[String, Map[String, Map[String, Permission]]]): Map[String, Map[String, Map[String, Permission]]] = role ++ role2
  }

  implicit class JoinAttributes(attribute: String) {
    def &(attribute2: String): String = attribute + Attribute_Splitter + attribute2
  }

  implicit class AddAttributes(resourceWithAction: Map[String, Map[String, Permission]]) {
    def attributes(attributes: String): Map[String, Map[String, Permission]] = {
      val resource = resourceWithAction.head._1
      val action = resourceWithAction.head._2.head._1
      val permission = resourceWithAction.head._2.head._2
      resourceWithAction.updated(resource, resourceWithAction(resource).updated(action, permission.copy(attributes = Some(attributes.split(Attribute_Splitter).toList))))
    }
  }

}
