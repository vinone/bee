/*
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is
 * Bluesoft Consultoria em Informatica Ltda.
 * Portions created by the Initial Developer are Copyright (C) 2011
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 */

package br.com.bluesoft.bee;

import br.com.bluesoft.bee.service.BeeWriter

public class BeeSchemaModule implements BeeWriter {

	def usage() {
		println "usage: bee <options> schema:action <parameters>"
		println "Actions:"
		println "         schema:generate connection [object] - generates an entire schema or single object, if specified"
		println "         schema:validate connection [object] - validates an entire schema or single object, if specified"
		println "         schema:recreate_mysql [object] - build a MySql DDL script"
		println "         schema:recreate_oracle [object] - build a Oracle DDL script"
		println "         schema:check - validate structure correctness"
	}

	def parseOptions(options) {
		def arguments = options.arguments
		def action = options.actionName
		def actionRunner = null

		switch(action) {
			case "generate":
				actionRunner = new BeeSchemaGeneratorAction(options: options, out: this)
				break
			case "validate":
				actionRunner = new BeeSchemaValidatorAction(options: options, out: this)
				break;
			case "recreate_mysql":
				actionRunner = new BeeMySqlSchemaCreatorAction(options: options, out: this)
				break;
			case "recreate_oracle":
				actionRunner = new BeeOracleSchemaCreatorAction(options: options, out: this)
				break;
			case "check":
				actionRunner = new BeeSchemaCheckerAction(options: options, out: this)
				break;
			default:
				usage();
				System.exit 0
		}

		if(!actionRunner.validateParameters()) {
			usage();
			System.exit 0
		}

		return actionRunner
	}

	def run(options) {
		def actionRunner = parseOptions(options)
		if(actionRunner)
			if(!actionRunner.run())
				System.exit(1)
	}

	void log(String msg) {
		println msg
	}
}
