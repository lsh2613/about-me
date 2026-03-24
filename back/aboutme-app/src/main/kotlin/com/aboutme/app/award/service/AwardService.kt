package com.aboutme.app.award.service

import com.aboutme.app.award.port.`in`.AwardUseCase
import com.aboutme.app.award.port.out.AwardCommandPort
import com.aboutme.app.award.port.out.AwardQueryPort
import com.aboutme.app.award.service.dto.command.AwardSyncCommand
import com.aboutme.app.award.service.dto.rep.AwardDetailRep
import com.aboutme.app.common.util.CommonValidationUtil
import com.aboutme.core.award.domain.Award
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AwardService(
    private val awardCommandPort: AwardCommandPort,
    private val awardQueryPort: AwardQueryPort,
) : AwardUseCase {
    @Transactional
    override fun sync(commands: List<AwardSyncCommand>) {
        CommonValidationUtil.validateSequence(commands.map { it.seq })

        deleteNotInCommands(commands)
        saveOrUpdate(commands)
    }

    private fun saveOrUpdate(commands: List<AwardSyncCommand>) {
        commands.forEach { command ->
            command.id?.let {
                update(it, command)
            } ?: save(command)
        }
    }

    private fun save(command: AwardSyncCommand) {
        Award(
            name = command.name,
            issuer = command.issuer,
            issueDate = command.issueDate,
            description = command.description,
            seq = command.seq,
        ).also { awardCommandPort.save(it) }
    }

    private fun update(
        it: Long,
        command: AwardSyncCommand,
    ) {
        awardQueryPort.findOrThrow(it).apply {
            update(
                name = name,
                issuer = issuer,
                issueDate = issueDate,
                description = description,
                seq = seq,
            )
        }.also { awardCommandPort.update(it) }
    }

    private fun deleteNotInCommands(commands: List<AwardSyncCommand>) {
        val awards = awardQueryPort.findAll()
        val deleteIds = awards.map { it.id!! } - commands.mapNotNull { it.id }.toSet()
        awardCommandPort.delete(deleteIds)
    }

    @Transactional(readOnly = true)
    override fun readDetails(): List<AwardDetailRep> {
        return awardQueryPort.findAll().map { AwardDetailRep.of(it) }
    }

    @Transactional
    override fun deleteAll() {
        awardCommandPort.deleteAll()
    }
}
