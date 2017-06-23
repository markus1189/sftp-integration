package org.markushauck.sftp

import com.jcraft.jsch._

import scala.collection.JavaConverters._

case class SftpConfig(user: String, password: String, host: String, port: Int)

object Main {

  def listSftp(config: SftpConfig): List[ChannelSftp#LsEntry] = {
    val jsch = new JSch

    var release: List[() => Unit] = List()

    try {
      val session = jsch.getSession(config.user, config.host, config.port)
      release = (() => session.disconnect()) +: release

      session.setPassword(config.password)
      session.setConfig("StrictHostKeyChecking", "no")
      session.connect()

      val channel: ChannelSftp = session.openChannel("sftp").asInstanceOf[ChannelSftp]
      release = (() => channel.disconnect()) +: release

      channel.connect()

      channel.ls("/").asScala.map(_.asInstanceOf[ChannelSftp#LsEntry])(collection.breakOut)
    } finally {
      release.foreach(_())
    }
  }
}
