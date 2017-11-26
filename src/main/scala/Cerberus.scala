import Permission.EmptyPermission
import Types.Data

class Cerberus(data: Data) {
  def permission(role: String, resource: String, action: String): Permission = {
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