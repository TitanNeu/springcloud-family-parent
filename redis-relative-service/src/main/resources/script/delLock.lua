local lock = KEYS[1]
local lockVal = ARGV[1]
if redis.call('get',lock) == lockVal then
    redis.call('del',lock)
else
    return 0
end