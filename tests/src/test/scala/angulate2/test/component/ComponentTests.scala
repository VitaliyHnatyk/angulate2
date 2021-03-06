//     Project: angulate2 (https://github.com/jokade/angulate2)
// Description: Test cases for @Component

// Copyright (c) 2015 Johannes.Kastner <jokade@karchedon.de>
//               Distributed under the MIT License (see included file LICENSE)
package angulate2.test.component

import angulate2._
import angulate2.core.Component
import angulate2.test.TestBase

import scala.scalajs.js

object ComponentTests extends TestBase {

  ng.register[Component1]
  ng.register[Component2]

  val tests = TestSuite {

    'simple-{
      withHtml("""<comp1 id="comp1"></comp1>"""){
        assert( jq("#test").children.length == 0 )
        ng.bootstrapWith[Component1]
        laterWithChildren("#comp1"){ c =>
          assert( c.first().text() == "Hello, world!" )
        }
      }
    }

    'withDirectives-{
      withHtml("""<comp2 id="comp2"></comp2>"""){
        assert( jq("#test2").children.length == 0 )
        ng.bootstrapWith[Component2]
        laterWithChildren("#comp2"){ c =>
          assert( c.length == 4 ) // the first element is the template
        }
      }
    }
  }

}


@Component(
  selector = "comp1",
  template = "<span>Hello, world!</span>"
)
class Component1


@Component(
  selector = "comp2",
  template = """<span *ng-for="#name of names">{{name}}</span>""",
  directives = js.Array(ng.NgFor)
)
class Component2 {
  val names = js.Array("Luke","Lea","Han")
}

@Injectable
class Service1 {
  val names = js.Array("Luke","Lea","Han")
}
