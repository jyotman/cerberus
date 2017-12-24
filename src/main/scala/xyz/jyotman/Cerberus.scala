package xyz.jyotman

import xyz.Types.Data
import xyz.jyotman.Permission.EmptyPermission

class Cerberus(data: Data) {

  def can(role: String, action: String, resource: String): Permission = {
    if (!data.contains(role))
      throw new Exception(raw"""Role "$role" does not exist""")
    else if (data(role).contains(resource) && data(role)(resource).contains(action))
      data(role)(resource)(action)
    else
      EmptyPermission
  }

  def roles: Iterable[String] = data.keys
}

object Cerberus {

  def apply(data: Data): Cerberus = new Cerberus(data)
}