package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import java.net.URI
import play.api._
import play.api.mvc._
import org.joda.time._
import models._
import dal._

class Test(dao: AsyncStorage)  extends Controller {
  def delete = Action {
    Async {
      for (
        droppedUsers <- dao.deleteAllUsers();
        droppedFeeds <- dao.deleteAllFeeds()
      ) yield {
        if (droppedUsers && droppedFeeds) {
          Ok("Deleted MidorI database.")
        } else {
          Ok("Failed to delete MidorI database.")
        }
      }
    }
  }
  
  def create = Action {
    Async {      
      val insertions = Seq(
        dao.createUser(User(
	      "banana", 
	      Seq(
	        Subscription("http://www.mspaintadventures.com/rss/rss.xml", Seq()),
	        Subscription("http://feeds.feedburner.com/JonSkeetCodingBlog?format=xml", Seq())
	      )
	    )),
	    dao.createFeed(Feed(
	      "http://www.mspaintadventures.com/rss/rss.xml",
	      "MS Paint Adventures",
	      "",
	      new URI("http://www.mspaintadventures.com"),
	      DateTime.now().plusDays(1),
	      None
	    )),
	    dao.createFeed(Feed(
	      "http://feeds.feedburner.com/JonSkeetCodingBlog?format=xml",
	      "Jon Skeet: Coding Blog",
	      "C#, .NET, Java, software development etc\n**This is my personal blog. The views expressed on these pages are mine alone and not those of my employer.**",
	      new URI("http://msmvps.com/blogs/jon_skeet/default.aspx"),
	      DateTime.now(),
	      Some(Seq(
	        Entry(
	          "d67277c4-116b-43f1-b688-e9ef184ea916:1825236", 
	          DateTime.now(),
	          "Background",
	          new URI("http://feedproxy.google.com/~r/JonSkeetCodingBlog/~3/uPCzYxuuzoI/the-open-closed-principle-in-review.aspx"),
	          "<p>I&#39;ve been to a few talks on <a href=\"http://en.wikipedia.org/wiki/SOLID_(object-oriented_design)\">SOLID</a> before. Most of the principles seem pretty reasonable to me – but I&#39;ve never &quot;got&quot; the open-closed principle (OCP from here on). At CodeMash this year, I mentioned this to the wonderful <a href=\"http://truncatedcodr.wordpress.com/\">Cori Drew</a>, who said that she&#39;d been at a user group talk where she felt it was explained well. She mailed me a link to the <a href=\"http://usergroup.tv/videos/how-to-code-design-patterns\">user group video</a>, which I <em>finally</em> managed to get round to watching last week. (The OCP part is at around 1 hour 20.)</p> <p>Unfortunately I still wasn&#39;t satisfied, so I thought I&#39;d try to hit up the relevant literature. Obviously there are umpteen guides to OCP, but I decided to start with Wikipedia, and go from there. I mentioned my continuing disappointment on Twitter, and the conversation got lively. Uncle Bob Martin (one of the two &quot;canonical sources&quot; for OCP) wrote a follow-up blog post, and I decided it would be worth writing one of my own, too, which you&#39;re now reading.</p> <p>I should say up-front that in some senses this blog post isn&#39;t so much about the details of the open-closed principle, as about the importance of careful choice of terminology at all levels. As we&#39;ll see later, when it comes to the &quot;true&quot; meaning of OCP, I&#39;m pretty much with Uncle Bob: it&#39;s motherhood and apple pie. But I believe that meaning is much more clearly stated in various other principles, and that OCP as the expression of an idea is doing more harm than good.</p>"
	        ),
	        Entry(
	          "d67277c4-116b-43f1-b688-e9ef184ea916:1825236", 
              DateTime.now(),
              "Reading material",
              new URI("http://feedproxy.google.com/~r/JonSkeetCodingBlog/~3/uPCzYxuuzoI/the-open-closed-principle-in-review.aspx"),
              "<ul> <li><a href=\"http://en.wikipedia.org/wiki/Open/closed_principle\">Wikipedia OCP entry</a> </li> <li><a href=\"http://www.objectmentor.com/resources/articles/ocp.pdf\">Uncle Bob Martin&#39;s 1996 paper</a> </li> <li><a href=\"http://codecourse.sourceforge.net/materials/The-Importance-of-Being-Closed.pdf\">Craig Larman&#39;s comparison with Protected Variation</a> </li> <li><a href=\"http://blog.8thlight.com/uncle-bob/2013/03/08/AnOpenAndClosedCase.html\">Uncle Bob&#39;s blog response</a> </li> </ul>"
            ),
            Entry(
              "d67277c4-116b-43f1-b688-e9ef184ea916:1825236", 
              DateTime.now(),
              "So what is it? (Part 1 – high level)",
              new URI("http://feedproxy.google.com/~r/JonSkeetCodingBlog/~3/uPCzYxuuzoI/the-open-closed-principle-in-review.aspx"),
              "<p>This is where it gets interesting. You see, there appear to be several different interpretation of the principle – some only subtly distinct, others seemingly almost unrelated. Even without looking anything up, I knew an expanded version of the name:</p> <blockquote> <p>Modules should be open for extension and closed for modification.</p> </blockquote> <p>The version quoted in Wikipedia and in Uncle Bob&#39;s paper actually uses &quot;Software entities (classes, modules, functions, etc.)&quot; instead of modules, but I&#39;m not sure that really helps. Now I&#39;m not naïve enough to expect everything in a principle to be clear just from the title, but I do expect <em>some</em> light to be shed. In this case, unfortunately I&#39;m none the wiser. &quot;Open&quot; and &quot;closed&quot; sound permissive and restrictive respectively, but without very concrete ideas about what &quot;extension&quot; and &quot;modification&quot; mean, it&#39;s hard to tell much more.</p> <p>Fair enough – so we read on to the next level. Unfortunately I don&#39;t have Bertrand Meyer&#39;s &quot;Object-Oriented Software Construction&quot; book (which I take to be the original), but Uncle Bob&#39;s paper is freely available. Wikipedia&#39;s summary of Meyer&#39;s version is:</p> <blockquote> <p>The idea was that once completed, the implementation of a class could only be modified to correct errors; new or changed features would require that a different class be created. That class could reuse coding from the original class through inheritance. The derived subclass might or might not have the same interface as the original class.</p> <p>Meyer&#39;s definition advocates implementation inheritance. Implementation can be reused through inheritance but interface specifications need not be. The existing implementation is closed to modifications, and new implementations need not implement the existing interface.</p> </blockquote> <p>And Uncle Bob&#39;s high level description is:</p> <blockquote> <p>Modules that conform to the open-closed principle have two primary attributes.</p> <ol> <li>They are &quot;Open For Extension&quot;. This means that the behavior of the module can be extended. That we can make the module behave in new and different ways as the requirements of the application change, or to meet the needs of new applications. </li> <li>They are &quot;Closed for Modiﬁcation&quot;. The source code of such a module is inviolate. No one is allowed to make source code changes to it. </li> </ol> </blockquote> <p>I immediately took a dislike to both of these descriptions. Both of them specifically say that the source code can&#39;t be changed, and the description of Meyer&#39;s approach to &quot;make a change by extending a class&quot; feels like a ghastly abuse of inheritance to me... and goes firmly against my (continued) belief in Josh Bloch&#39;s advice of &quot;design for inheritance or prohibit it&quot; – where in the majority of cases, designing a <em>class</em> for inheritance involves an awful lot of work for little gain. Designing an <em>interface</em> (or pure abstract class) still involves work, but with fewer restrictions and risks.</p> <p>Craig Larman&#39;s article uses the term &quot;closed&quot; in a <em>much</em> more reasonable way, to my mind:</p> <blockquote> <p>Also, the phrase &quot;closed with respect to X&quot; means that clients are not affected if X changes.</p> </blockquote> <p>When I say &quot;more reasonable way&quot; I mean in terms of how I want to write code... not in terms of the use of the word &quot;closed&quot;. This is simply not how the word &quot;closed&quot; is used elsewhere in my experience. In the rare cases where &quot;closed&quot; <em>is</em> used with &quot;to&quot;, it&#39;s usually in terms of what&#39;s not allowed in: &quot;This bar is closed to under 18s&quot; for example. Indeed, that&#39;s how I read &quot;closed to modification&quot; and that appears to be backed up by the two quotes which say that once a class is complete, the source code cannot be changed.</p> <p>Likewise the meaning of &quot;open for extension&quot; seems unusual to me. I&#39;d argue that the intuitive meaning is &quot;can be extended&quot; – where the use of the term &quot;extended&quot; certainly nods towards inheritance, even if it&#39;s not intended meaning. If the idea is &quot;we can make the module behave differently&quot; – as Uncle Bob&#39;s description suggests – then &quot;open for extension&quot; is a very odd way of expressing that idea. I&#39;d even argue that in the example given later, it&#39;s not the &quot;open&quot; module that behaves differently – it&#39;s the <em>combination</em> of the module and its collaborators, acting as a unified program, which behaves differently after some aspects are modified.</p>"
            ),
            Entry(
              "d67277c4-116b-43f1-b688-e9ef184ea916:1825236", 
              DateTime.now(),
              "So what is it? (Part 2 – more detail)",
              new URI("http://feedproxy.google.com/~r/JonSkeetCodingBlog/~3/uPCzYxuuzoI/the-open-closed-principle-in-review.aspx"),
              "<p>Reading on through the rest of Uncle Bob&#39;s paper, the ideas become much more familiar. There&#39;s a reasonable example of a function which is asked to draw a collection of shapes: the bad code is aware of all the types of shape available, and handles each one separately. The good code uses an abstraction where each shape (Circle, Square) knows how to draw itself and inherits from a common base class (Shape). Great stuff... but what&#39;s that got to do with what was described above? How are the concepts of &quot;open&quot; and &quot;closed&quot; clarified?</p> <p>The answer is that they&#39;re not. The word &quot;open&quot; doesn&#39;t occur anywhere in the rest of the text, other than as part of the term &quot;open-closed principle&quot; or as a label for &quot;open client&quot;. While it&#39;s perhaps rather easier to see this in hindsight, I suspect that any time a section which is meant to clarify a concept doesn&#39;t use some of the key words used to describe the concept in a nutshell, that description should be treated as suspect.</p> <p>The word &quot;closed&quot; appears more often, but <em>only</em> in terms of &quot;closed against&quot; which is never actually defined. (Is &quot;closed against&quot; the same as &quot;closed for&quot;?) Without Craig Larman&#39;s explanation, sentences like this make little sense to me:</p> <blockquote> <p>The function DrawAllShapes does not conform to the open-closed principle because it cannot be closed against new kinds of shapes.</p> </blockquote> <p>Even Craig&#39;s explanation feels somewhat at odds with Uncle Bob&#39;s usage, as it talks about <em>clients</em> being affected. This is another of the issues I have with the original two descriptions: they talk about a <em>single module</em> being open/closed, whereas we&#39;re dealing with abstractions where there are naturally at least two pieces of code involved (and usually three). Craig&#39;s description of changes in one module not affecting clients is describing a <em>relationship</em> – which is a far more useful way of approaching things. Even thinking about the shape example, I&#39;m getting increasingly confused about exactly what&#39;s open and what&#39;s closed. It feels to me like it&#39;s neither the concrete shape classes nor the shape-drawing code which is open or closed – it&#39;s the interface between the two; the abstract Shape class. After all, these statements seem reasonable:</p> <ul> <li>The Shape class is open for extension: there can be many different concrete subclasses, and code which only depends on the Shape class doesn&#39;t need to know about them in order to use them when they are presented as shapes. </li> <li>The Shape class is closed for modification: no existing functions can be removed (as they may be relied on by existing clients) and no new pure virtual functions can be added (as they will not be implemented by existing subclasses). </li> </ul> <p>It&#39;s still not how I&#39;d choose to express it, but at least it feels like it makes sense in very concrete terms. It doesn&#39;t work well with how Uncle Bob uses the term &quot;closed&quot; though, so I still think I may be on a different page when it comes to that meaning. (Uncle Bob does also make the point that any significant program isn&#39;t going to adhere to the principle completely in every part of the code – but in order to judge where it&#39;s appropriate to be closed, I do really need to understand what being closed means.)</p> <p>Just to make it crystal clear, other than the use of the word &quot;closed,&quot; the low-level description of what&#39;s good and what&#39;s bad, and why, is absolutely fine. I really have no problems with it. As I said at the start, the idea being expressed makes perfect sense. It just doesn&#39;t work (for me) when expressed in the terms used at a higher level.</p>"
            )
	      ))
	    ))
      )
      
      Future.reduce(insertions) { _ && _ } map { 
        if (_) { 
	      Ok("Created MidorI database.")
	    } else {
	      Ok("Failed to create MidorI database.")
	    }
      }
    }
  }
  
}
