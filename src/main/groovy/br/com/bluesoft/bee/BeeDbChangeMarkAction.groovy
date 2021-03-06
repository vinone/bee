package br.com.bluesoft.bee

import br.com.bluesoft.bee.dbchange.DbChangeManager
import br.com.bluesoft.bee.model.Options
import br.com.bluesoft.bee.service.BeeWriter


class BeeDbChangeMarkAction {

	Options options
	BeeWriter out

	def run() {
		def migrationId = options.arguments[1]
		new DbChangeManager(configFile: options.configFile, path: options.dataDir.absolutePath, clientName: options.arguments[0], logger: out).mark(migrationId)
	}
}
