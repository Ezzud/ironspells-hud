# 🔵 Iron's Spells HUD
<p align="center">
  <img width=200 src="https://github.com/Ezzud/ironspells-hud/blob/client-only/src/main/resources/assets/logo.png?raw=true" alt="Logo"/>
</p>

### Recommended with FancyMenu and SpiffyHUD


## 🖥️ CLIENT-SIDE ONLY

## Requirements
- [Iron's Spells & Spellbooks](https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks)
- [FancyMenu](https://www.curseforge.com/minecraft/mc-mods/fancymenu) (Optional but I don't know why you would use this without it)
- [SpiffyHUD](https://www.curseforge.com/minecraft/mc-mods/spiffyhud) (Optional but I don't know why you would use this without it)

## Description
Iron's Spells HUD is a client-side mod that provides the Mana and Max Mana data values to work with FancyMenu and SpiffyHUD.

The original Iron's Spells mods doesn't send these values when playing on a multiplayer server and I wanted to do a custom mana bar with SpiffyHUD.

The values should work with any mod using the nbt data, but it was originally made to work with SpiffyHUD

## 📜 Usage with SpiffyHUD
Use the following placeholders on any text component to display the values
- Current Mana:
```
{"placeholder":"player_nbt_data","values":{"path":"neoforge:attachments.ironspellshud:current_mana"}}
```

- Max Mana:
```
{"placeholder":"player_nbt_data","values":{"path":"neoforge:attachments.ironspellshud:max_mana"}}
```
