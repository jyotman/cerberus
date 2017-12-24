package xyz.jyotman

import scala.collection.immutable.HashMap

object Dsl {

  val Attribute_Splitter = "SPLITTER"

  implicit class AssignResourceToRole(role: String) {
    def can(resourcesWithActions: Map[String, Map[String, Permission]]): Map[String, Map[String, Map[String, Permission]]] = HashMap(role -> resourcesWithActions)
  }

  implicit class JoinResourceAndActionAnyPermission(action: String) {
    def any(resource: String): Map[String, Map[String, Permission]] = HashMap(resource -> HashMap(action -> Permission(any = true)))
  }

  implicit class JoinResourceAndActionOwnPermission(action: String) {
    def own(resource: String): Map[String, Map[String, Permission]] = HashMap(resource -> HashMap(action -> Permission(own = true)))
  }

  implicit class JoinResource(resourceWithActions: Map[String, Map[String, Permission]]) {
    def also(resourceWithActions2: Map[String, Map[String, Permission]]): Map[String, Map[String, Permission]] = {
      val resource = resourceWithActions2.head._1
      if (resourceWithActions.contains(resource)) {
        val actionToBeAdded = resourceWithActions2.head._2.head._1
        val existingActionsWithPermission = resourceWithActions(resource)
        if (existingActionsWithPermission.contains(actionToBeAdded)) {
          val updatedPermission = existingActionsWithPermission(actionToBeAdded).merge(resourceWithActions2.head._2.head._2)
          resourceWithActions.updated(resource, resourceWithActions(resource).updated(actionToBeAdded, updatedPermission))
        }
        else
          resourceWithActions.updated(resource, resourceWithActions2.head._2 ++ resourceWithActions(resource))
      }
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
      val seperatedAttributes = attributes.split(Attribute_Splitter).toList
      val permissionWithAttributes =
        if (permission.any)
          permission.copy(anyAttributes = Some(seperatedAttributes))
        else
          permission.copy(ownAttributes = Some(seperatedAttributes))
      resourceWithAction.updated(resource, resourceWithAction(resource).updated(action, permissionWithAttributes))
    }
  }

}
