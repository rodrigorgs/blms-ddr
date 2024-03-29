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

JARS='../../jars/easyaccept.jar:../../jars/jeplite-0.8.7a-bin.jar:../../jars/bloat-1.0.jar:../../jars/db4o-6.1-db4ounit.jar:../../jars/db4o-6.1-java5.jar:../../jars/db4o-6.1-nqopt.jar'
i = 0
files.permute do |l|
	i += 1
	puts "#{i}. Rodando os testes na seguinte ordem: #{l.join(', ')}"
	params = l.inject('') { |sum, e| sum + (template % e) + ' ' } 
	s = "java -cp ../../bin:#{JARS} easyaccept.EasyAccept blms.facade.BlmsFacade #{params}"
	puts `#{s}`
	if $?.to_i != 0 then exit end
end
