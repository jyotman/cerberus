package xyz.jyotman

import xyz.jyotman.Permission.EmptyPermission
import xyz.Types.Data

class Cerberus(data: Data) {

  def can(role: String, action: String, resource: String): Permission = {
    if (!data.contains(role))
      throw new Exception(raw"""Role "$role" does not exist""")
    else if (data(role).contains(resource) && data(role)(resource).contains(action))
      data(role)(resource)(action)
    else
      EmptyPermission
  }
}

object Cerberus {

  def apply(data: Data): Cerberus = new Cerberus(data)
}