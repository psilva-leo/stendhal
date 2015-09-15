/***************************************************************************
 *                   (C) Copyright 2003-2014 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

"use strict";

(function() {

	var HEALTH_BAR_HEIGHT = 6;



/**
 * RPEntity
 */
marauroa.rpobjectFactory.rpentity = marauroa.util.fromProto(marauroa.rpobjectFactory.activeEntity, {
	zIndex: 8000,
	drawY: 0,
	spritePath: "",
	titleStyle: "#FFFFFF",
	_target: null,
	attackSprite: null,

	set: function(key, value) {
		marauroa.rpobjectFactory.rpentity.proto.set.apply(this, arguments);
		if (key == "text") {
			this.say(value);
		} else if (key in ["hp", "base_hp"]) {
			this[key] = parseInt(value);
		} else if (key == "target") {
			if (this._target) {
				this._target.onAttackStopped(this);
			}
			this._target = marauroa.currentZone[value];
			if (this._target) {
				this._target.onTargeted(this);
			}
		}
	},
	
	unset: function(key) {
		if (key == "target" && this._target) {
			this._target.onAttackStopped(this);
			this._target = null;
		}
		delete this[key];
	},

	isVisibleToAction: function(filter) {
		return true;
	},

	/** 
	 * says a text
	 */
	say: function (text) {
		if (!marauroa.me) {
			return;
		}
		if (marauroa.me.isInHearingRange(this)) {
			if (text.match("^!me") == "!me") {
				stendhal.ui.chatLog.addLine("emote", text.replace(/^!me/, this.title));
			} else {
				stendhal.ui.chatLog.addLine("normal", this.title + ": " + text);
			}
		}
	},

	/**
	 * draw an outfix part
	 *
	 * @param ctx   ctx
	 * @param part  part
	 * @param index index
	 */
	drawOutfitPart: function(ctx, part, index) {
		var n = index;
		if (index < 10) {
			n = "00" + index;
		} else if(index < 100) {
			n = "0" + index;
		}
		var filename = "/data/sprites/outfit/" + part + "/" + part + "_" + n + ".png";
		this.drawSprite(ctx, filename);
	},

	/** 
	 * draw RPEntities
	 */
	draw: function(ctx) {
		this.drawCombat(ctx);
		var filename;
		if (typeof(this.outfit) != "undefined") {
			this.drawOutfitPart(ctx, "body", (this.outfit % 100));
			this.drawOutfitPart(ctx, "dress", (Math.floor(this.outfit/100) % 100));
			this.drawOutfitPart(ctx, "head", (Math.floor(this.outfit/10000) % 100));
			this.drawOutfitPart(ctx, "hair", (Math.floor(this.outfit/1000000) % 100));
		} else {
			filename = "/data/sprites/" + this.spritePath + "/" + this["class"];
			if (typeof(this.subclass) != "undefined") {
				filename = filename + "/" + this["subclass"];
			}
			filename = filename + ".png";
			this.drawSprite(ctx, filename)
		}
		this.drawAttack(ctx);
	},
	
	/**
	 * Draw colored ellipses (or rectangles on browsers that do not support
	 * ellipses) when the entity is being attacked, or is attacking the user.
	 */
	drawCombat: function(ctx) {
		if (this.attackers && this.attackers.size > 0) {
			ctx.lineWidth = 1;
			/*
			 * As of 2015-9-15 CanvasRenderingContext2D.ellipse() is not
			 * supported in most browsers. Fall back to rectangles on these.
			 * Also on Chrome 45.0.2454.85 ellipse() does not seem to support
			 * the begin angle parameter correctly, nor does the stroke 
			 * direction work as it should so it can't be used as a workaround.
			 * Currently the second ellipse part is drawn as a full ellipse, but
			 * the code below should eventually draw the right thing once
			 * browsers catch up. Probably.
			 */
			if (ctx.ellipse instanceof Function) {
				var xRad = this.width * 16;
				var yRad = this.height * 16 / Math.SQRT2;
				var centerX = this._x * 32 + xRad;
				var centerY = (this._y + this.height) * 32 - yRad;
				ctx.strokeStyle = "#4a0000";
				ctx.beginPath();
				ctx.ellipse(centerX, centerY, xRad, yRad, 0, Math.PI, false);
				ctx.stroke();
				ctx.strokeStyle = "#e60a0a";
				ctx.beginPath();
				ctx.ellipse(centerX, centerY, xRad, yRad, Math.PI, 2 * Math.PI, false);
				ctx.stroke();
			} else {
				ctx.strokeStyle = "#e60a0a";
				ctx.strokeRect(32 * this._x, 32 * this._y, 32 * this.width, 32 * this.height);
			}
		}
		if (this.getAttackTarget() === marauroa.me) {
			// See above about ellipses.
			if (ctx.ellipse instanceof Function) {
				var xRad = this.width * 16 - 1;
				var yRad = this.height * 16 / Math.SQRT2 - 1;
				var centerX = this._x * 32 + xRad + 1;
				var centerY = (this._y + this.height) * 32 - yRad - 1;
				ctx.strokeStyle = "#ffc800";
				ctx.beginPath();
				ctx.ellipse(centerX, centerY, xRad, yRad, 0, Math.PI, false);
				ctx.stroke();
				ctx.strokeStyle = "#ffdd0a";
				ctx.beginPath();
				ctx.ellipse(centerX, centerY, xRad, yRad, Math.PI, 2 * Math.PI, false);
				ctx.stroke();
			} else {
				ctx.strokeStyle = "#ffdd0a";
				ctx.strokeRect(32 * this._x + 1, 32 * this._y + 1, 32 * this.width - 2, 32 * this.height - 2);
			}
		}
	},
	
	drawSprite: function(ctx, filename) {
		var localX = this._x * 32;
		var localY = this._y * 32;
		var image = stendhal.data.sprites.get(filename);
		if (image.complete) {
			var nFrames = 3;
			var nDirections = 4;
			var yRow = this.dir - 1;
			// Ents are a hack in Java client too
			if (this["class"] == "ent") {
				nFrames = 1;
				nDirections = 2;
				yRow = Math.floor((this.dir - 1) / 2);
			}
			this.drawHeight = image.height / nDirections;
			this.drawWidth = image.width / nFrames;
			var drawX = ((this.width * 32) - this.drawWidth) / 2;
			var frame = 0;
			if (this.speed > 0 && nFrames != 1) {
				// % Works normally with *floats* (just whose bright idea was
				// that?), so use floor() as a workaround
				frame = Math.floor(Date.now() / 100) % nFrames;
			}
			var drawY = (this.height * 32) - this.drawHeight;
			ctx.drawImage(image, frame * this.drawWidth, yRow * this.drawHeight, this.drawWidth, this.drawHeight, localX + drawX, localY + drawY, this.drawWidth, this.drawHeight);
		}
	},

	drawTop: function(ctx) {
		var localX = this._x * 32;
		var localY = this._y * 32;

		this.drawHealthBar(ctx, localX, localY);
		this.drawTitle(ctx, localX, localY);
	},

	drawHealthBar: function(ctx, x, y) {
		var drawX = x + ((this.width * 32) - this.drawWidth) / 2;
		var drawY = y + (this.height * 32) - this.drawHeight - HEALTH_BAR_HEIGHT;
		
		ctx.fillStyle = "#E0E0E0";
		ctx.fillRect(drawX + 1, drawY + 1, this.drawWidth - 2, HEALTH_BAR_HEIGHT - 2);

		// Bar color
		var hpRatio = this.hp / this.base_hp;
		var red = Math.floor(Math.min((1 - hpRatio) * 2, 1) * 255);
		var green = Math.floor(Math.min(hpRatio * 2, 1) * 255);
		ctx.fillStyle = "rgb(".concat(red, ",", green, ",0)");
		ctx.fillRect(drawX + 1, drawY + 1, this.drawWidth * hpRatio - 2, HEALTH_BAR_HEIGHT - 2);

		ctx.strokeStyle = "#000000";
		ctx.lineWidth = 1;
		ctx.beginPath();
		ctx.rect(drawX, drawY, this.drawWidth - 1, HEALTH_BAR_HEIGHT - 1);
		ctx.stroke();
	},

	drawTitle: function(ctx, x, y) {
		if (typeof(this.title) != "undefined") {
			ctx.font = "14px Arial";
			ctx.fillStyle = "#A0A0A0";
			var textMetrics = ctx.measureText(this.title);
			var drawY = y + (this.height * 32) - this.drawHeight - HEALTH_BAR_HEIGHT;
			ctx.fillText(this.title, x + (this.width * 32 - textMetrics.width) / 2+2, drawY - 5 - HEALTH_BAR_HEIGHT);
			ctx.fillStyle = this.titleStyle;
			ctx.fillText(this.title, x + (this.width * 32 - textMetrics.width) / 2, drawY - 5 - HEALTH_BAR_HEIGHT);
		}
	},
	
	drawAttack: function(ctx) {
		if (this.attackSprite == null) {
			return;
		}
		if (this.attackSprite.expired()) {
			this.attackSprite = null;
			return;
		}
		var localX = this._x * 32;
		var localY = this._y * 32;
		this.attackSprite.draw(ctx, localX, localY, this.drawWidth, this.drawHeight);
	},

	// attack handling
	getAttackTarget: function() {
		// If the attack target id was read before the target was available,
		// _target does not point to the correct entity. Look up the target
		// again, if _target does not exist, but it should.
		if (!this._target && this.target) {
			this._target = marauroa.currentZone[this.target];
			if (this._target) {
				this._target.onTargeted(this);
			}
		}
		return this._target;
	},
	
	onDamaged: function(source, damage) {
		this.say(this.title + " got hit by " + source.title + " causing a damage of " + damage);
	},

	onBlocked: function(source) {
		
	},

	onMissed: function(source) {
		
	},

	onAttackPerformed: function(nature, ranged) {
		if (ranged) {
			var color;
			switch (nature) {
			case "0":
			default:
				color = "#c0c0c0";
			break;
			case "1":
				color = "#ff6400";
				break;
			case "2":
				color = "#8c8cff";
				break;
			case "3":
				color = "#fff08c";
				break;
			case "4":
				color = "#404040";
			}
			var tgt = this.getAttackTarget();
			this.attackSprite = (function(color, targetX, targetY) {
				return {
					initTime: Date.now(),
					expired: function() {
						return Date.now() - this.initTime > 180;
					},
					draw: function(ctx, x, y, entityWidth, entityHeight) {			
						var dtime = Date.now() - this.initTime;
						// We can use fractional "frame" for the lines. Just
						// draw the arrow where it should be at the moment.
						var frame = Math.min(dtime / 60, 4);
						
						var startX = x + entityWidth / 4;
						var startY = y + entityHeight / 4;
						
						var yLength = (targetY - startY) / 4;
						var xLength = (targetX - startX) / 4;

						startY += frame * yLength;
						var endY = startY + yLength;
						startX += frame * xLength;
						var endX = startX + xLength;
						
						ctx.strokeStyle = color;
						ctx.lineWidth = 2;
						ctx.moveTo(startX, startY);
						ctx.lineTo(endX, endY);
						ctx.stroke();
					}
				};
			})(color, (tgt.x + tgt.width / 2) * 32, (tgt.y + tgt.height / 2) * 32);
		} else {
			var imagePath;
			switch (nature) {
			case "0":
			default:
				imagePath = "/data/sprites/combat/blade_strike_cut.png";
			break;
			case "1":
				imagePath = "/data/sprites/combat/blade_strike_fire.png";
				break;
			case "2":
				imagePath = "/data/sprites/combat/blade_strike_ice.png";
				break;
			case "3":
				imagePath = "/data/sprites/combat/blade_strike_light.png";
				break;
			case "4":
				imagePath = "/data/sprites/combat/blade_strike_dark.png";
			}
			this.attackSprite = (function(imagePath, ranged, dir, width, height) {
				return {
					initTime: Date.now(),
					image: stendhal.data.sprites.get(imagePath),
					frame: 0,
					expired: function() {
						return Date.now() - this.initTime > 180;
					},
					draw: function(ctx, x, y, entityWidth, entityHeight) {
						if (!this.image.complete) {
							return;
						}
						var yRow = dir - 1;
						var drawHeight = this.image.height / 4;
						var drawWidth = this.image.width / 3;
						var dtime = Date.now() - this.initTime;
						var frame = Math.floor(Math.min(dtime / 60, 3));
						var centerX = x + (entityWidth - drawWidth) / 2;
						var centerY = y + (entityHeight - drawHeight) / 2;
						ctx.drawImage(this.image, frame * drawWidth, yRow * drawHeight,
								drawWidth, drawHeight, centerX, centerY, drawWidth, drawHeight);
					}
				};
			})(imagePath, ranged, this.dir);
		}
	},
	
	/**
	 * Called when this entity is selected as the attack target.
	 * 
	 * @param attacked The entity that selected this as the target
	 */
	onTargeted: function(attacker) {
		if (!this.attackers) {
			this.attackers = { size: 0 };
		}
		if (!(attacker.id in this.attackers)) {
			this.attackers[attacker.id] = true;
			this.attackers.size += 1;
		}
	},
	
	/**
	 * Called when an entity deselects this as its attack target.
	 * 
	 * @param attacker The entity that had this as the attack target, but
	 * 	stopped attacking
	 */
	onAttackStopped: function(attacker) {
		if (attacker.id in this.attackers) {
			delete this.attackers[attacker.id];
			this.attackers.size -= 1;
		}
	},
	
	destroy: function(obj) {
		if (this._target) {
			this._target.onAttackStopped(this);
		}
	}
});

})();