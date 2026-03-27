package com.aboutme.app.essay.service

import com.aboutme.app.common.util.CommonValidationUtil
import com.aboutme.app.essay.port.`in`.EssayUseCase
import com.aboutme.app.essay.port.out.EssayCommandPort
import com.aboutme.app.essay.port.out.EssayQueryPort
import com.aboutme.app.essay.service.dto.command.EssaySyncCommand
import com.aboutme.app.essay.service.dto.rep.EssayDetailRep
import com.aboutme.core.essay.domain.Essay
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EssayService(
    private val essayQueryPort: EssayQueryPort,
    private val essayCommandPort: EssayCommandPort,
) : EssayUseCase {
    @Transactional
    override fun sync(commands: List<EssaySyncCommand>) {
        CommonValidationUtil.validateSequence(commands.map { it.seq })

        deleteNotInCommands(commands)
        saveOrUpdate(commands)
    }

    private fun saveOrUpdate(commands: List<EssaySyncCommand>) {
        commands.forEach { command ->
            command.id?.let {
                update(it, command)
            } ?: save(command)
        }
    }

    private fun save(command: EssaySyncCommand) {
        Essay(
            title = command.title,
            content = command.content,
            seq = command.seq,
        ).also { essayCommandPort.save(it) }
    }

    private fun update(
        id: Long,
        command: EssaySyncCommand,
    ) {
        essayQueryPort.findOrThrow(id).apply {
            update(
                title = command.title,
                content = command.content,
                seq = command.seq,
            )
        }.also { essayCommandPort.update(it) }
    }

    private fun deleteNotInCommands(commands: List<EssaySyncCommand>) {
        val essays = essayQueryPort.findAll()
        val deleteIds = essays.map { it.id!! } - commands.mapNotNull { it.id }.toSet()
        essayCommandPort.delete(deleteIds)
    }

    @Transactional(readOnly = true)
    override fun readAll(): List<EssayDetailRep> {
        return essayQueryPort.findAll().map { EssayDetailRep.of(it) }
    }

    @Transactional
    override fun deleteAll() = essayCommandPort.deleteAll()
}
