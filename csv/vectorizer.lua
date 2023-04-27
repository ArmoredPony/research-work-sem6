local stem = require 'stemmer'
local dict = require 'dictionary'
local wordlist = {}
for word in pairs(dict) do wordlist[#wordlist+1] = word end
table.sort(wordlist)

---Returns a binary vector. Each number corresponds to a word in 'dictionary.lua'.
---Last values represent `category` in binary format.
---@param text string
---@param category 'sport'|'science'
---@return (0|1)[]
return function (text, category)
  local tokens = stem(text)
  local row = {}
  for _, w in ipairs(wordlist) do
    row[#row+1] = tokens[w] and 1 or 0
  end
  if category == 'sport' then
    row[#row+1] = 0
    row[#row+1] = 1
  elseif category == 'science' then
    row[#row+1] = 1
    row[#row+1] = 0
  else
    error("bad argument #2 to 'vectorizer' ('sport' or 'science' expected, got "
          ..tostring(category)
          ..')', 2)
  end
  return row
end