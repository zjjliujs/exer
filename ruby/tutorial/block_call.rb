#!/usr/bin/ruby

def test(&block)
   block.call
end
test { puts "Hello World!"}
