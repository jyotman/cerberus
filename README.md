# xyz.jyotman.Cerberus

<p align="center">
  <a href="https://yarnpkg.com/">
    <img alt="Yarn" src="https://raw.githubusercontent.com/jyotman/cerberus/master/cerberus_logo.png" width="350">
  </a>
</p>

Lightweight Role and Attribute based Access Control for Scala.

Easy to use DSL for assigning permissions to different roles.

Read more about [RBAC](https://en.wikipedia.org/wiki/Role-based_access_control) and [ABAC](https://en.wikipedia.org/wiki/Attribute-based_access_control).

## Installation

    resolvers += Resolver.bintrayRepo("jyotman","maven")

    libraryDependencies += "jyotman" %% "cerberus" % "0.0.2"
    
## Basic Example

```scala
import xyz.Types.Data
import xyz.jyotman.Cerberus
import xyz.jyotman.Dsl._
    
val data: Data = 
    ("user" can (
      ("read" any "project" attributes "title" & "description" & "!createdOn") also
        ("read" own "project" attributes "title" & "description") also
        ("create" own "project") also
        ("update" own "profile")       
      )) and
      ("curator" can (
        ("read" any "project") also
          ("update" any "project") also
          ("delete" any "project")
      ))
    
val cerberus = xyz.jyotman.Cerberus(data)

cerberus.can("user", "create", "project").any // false
cerberus.can("user", "create", "project").own // true
cerberus.can("user", "read", "project").any(List("createdOn")) // false
cerberus.can("user", "read", "project").any(List("title", "description")) // true
```
    
## Documentation

Work in Progress

### Inspired by [Access Control](https://github.com/onury/accesscontrol).