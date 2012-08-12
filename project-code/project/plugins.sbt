// Comment to get more information during initialization
logLevel := Level.Info

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "maven-central" at "http://repo1.maven.org/maven2"

resolvers += "maven-ibiblio" at "http://www.ibiblio.org/maven2"

resolvers += "google" at "http://google-maven-repository.googlecode.com/svn/repository"

resolvers += "glassfish" at "http://download.java.net/maven/glassfish"

resolvers += "JBoss Backup at Atlassian" at "https://maven.atlassian.com/content/repositories/jboss-releases"

resolvers += "openqa" at "http://archiva.openqa.org/repository/releases"

resolvers += "org.jboss.repository.public" at "http://repository.jboss.org/nexus/content/groups/public/"

resolvers += "org.jboss.repository.public-jboss" at "http://repository.jboss.org/nexus/content/groups/public-jboss"

resolvers += "javanet" at "http://download.java.net/maven/2"

resolvers += "apache-incubator" at "http://people.apache.org/repo/m2-incubating-repository"

resolvers += "c5-public-repository" at "http://mvn.carbonfive.com/public"

resolvers += "raisercostin-public-releases" at "http://raisercostin.googlecode.com/svn/maven2"

resolvers += "eessi.snapshots" at "https://webgate.ec.europa.eu/CITnet/svn/eessi/maven2/snapshots"

resolvers += "Central Repository" at "http://repo.maven.apache.org/maven2"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.2")
