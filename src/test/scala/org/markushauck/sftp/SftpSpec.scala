package org.markushauck.sftp

import com.whisk.docker.impl.spotify.DockerKitSpotify
import org.scalatest._
import org.scalactic._
import com.whisk.docker.scalatest._
import org.scalatest.time.{Second, Seconds, Span}

class SftpSpec
    extends WordSpec
    with Matchers
    with TypeCheckedTripleEquals
    with DockerTestKit
    with DockerKitSpotify
    with DockerSftpService {

  implicit val pc = PatienceConfig(Span(20, Seconds), Span(1, Second))

  "Sftp example" should {
    "use the docker container" in {
      val result = Main.listSftp(SftpConfig(sftpUser, sftpPassword, "localhost", exposedSftpPort))

      result should have length 3
      result.exists(lsEntry => lsEntry.getFilename == sftpDirectory) should ===(true)
    }
  }
}
