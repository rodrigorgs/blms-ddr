#!/usr/bin/ruby -w

template = '../test/easyaccept/us-%s.txt '
files = %w(users leagues win-loss standings join)

# http://wiki.rubygarden.org/Ruby/page/show/ArrayPermute
# Permute an array, and call a block for each permutation
# Author: Paul Battley

    class Array
        def permute(prefixed=[])
            if (length < 2)
                # there are no elements left to permute
                yield(prefixed + self)
            else
                # recursively permute the remaining elements
                each_with_index do |e, i|
                    (self[0,i]+self[(i+1)..-1]).permute(prefixed+[e]) { |a| yield a }
                end
            end
        end
    end

files.permute do |l|
	puts "Rodando os testes na seguinte ordem: #{l.join(', ')}"
	params = l.inject('') { |sum, e| sum + (template % e) + ' ' } 
	s = "java -cp ../../bin:../../jars/easyaccept.jar:../../jars/jeplite-0.8.7a-bin.jar easyaccept.EasyAccept blms.facade.BlmsFacade #{params}"
	puts `#{s}`
end
