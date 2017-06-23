package org.markushauck.sftp

import com.whisk.docker.{DockerContainer, DockerKit, DockerReadyChecker}

trait DockerSftpService extends DockerKit {

  private val defaultSftpPort = 22
  val exposedSftpPort = 2222
  val sftpUser = "user"
  val sftpPassword = "password"
  val sftpDirectory = "upload"

  val containerName = "atmoz/sftp:alpine"

  val sftpContainer = DockerContainer(containerName)
    .withPorts(defaultSftpPort -> Some(exposedSftpPort))
    .withReadyChecker(
      DockerReadyChecker.LogLineContains("Server listening on"))
    .withCommand(s"$sftpUser:$sftpPassword:::$sftpDirectory")

  abstract override def dockerContainers: List[DockerContainer] =
    sftpContainer :: super.dockerContainers
}
