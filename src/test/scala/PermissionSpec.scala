import org.scalatest.FlatSpec
import xyz.jyotman.Permission

class PermissionSpec extends FlatSpec {

  "A Permission" should "expose all member variables" in {
    val permission = Permission()
    assert(permission.any === false)
    assert(permission.own === false)
    assert(permission.anyAttributes.isEmpty)
    assert(permission.ownAttributes.isEmpty)
  }

  it should "satisfy any check without attributes when correct permission given" in {
    val permission = Permission(any = true)
    assert(permission.any === true)
  }

  it should "satisfy own check without attributes when correct permission given" in {
    val permission = Permission(own = true)
    assert(permission.own === true)
  }

  it should "satisfy any check with attributes when anyAttributes given" in {
    val permission = Permission(any = true, anyAttributes = Some(List("name", "description")))
    assert(permission.any(List("name")) === true)
  }

  it should "satisfy own check with attributes when ownAttributes given" in {
    val permission = Permission(own = true, ownAttributes = Some(List("name", "age")))
    assert(permission.own(List("age")) === true)
  }

  it can "be cloned with any attribute" in {
    val permission = Permission(own = true, any = true, Some(List("name")), Some(List("name", "description")))
    val newPermission = permission.copy(own = false, ownAttributes = None)
    assert(newPermission.own === false)
    assert(newPermission.any === true)
    assert(newPermission.ownAttributes.isEmpty)
    assert(newPermission.anyAttributes.isDefined)
  }

  it can "be merged with other permission" in {
    val permission1 = Permission(own = true, ownAttributes = Some(List("name")))
    val permission2 = Permission(any = true, anyAttributes = Some(List("name", "description")))
    val mergedPermission = permission1 merge permission2
    assert(mergedPermission.own === true)
    assert(mergedPermission.any === true)
    assert(mergedPermission.ownAttributes === Some(List("name")))
    assert(mergedPermission.anyAttributes === Some(List("name", "description")))
  }

  it should "have no permission when empty" in {
    val permission = Permission.EmptyPermission
    assert(permission.own === false)
    assert(permission.any === false)
  }
}