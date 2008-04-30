#!/usr/bin/ruby -w

class Meth
	attr_accessor :name, :parameters, :ret, :throws, :lines

	def initialize(name='')
		@name = name
		@parameters = []
		@ret = 'void'
		@throws = false
		@lines = Hash.new
	end

	def to_s
		comment = '// from '
		@lines.each_pair { |k,v| comment += "#{k}:#{v.join(',')} " }
		return <<EOT
	#{comment}
	public #{@ret} #{@name}(#{@parameters.join(', ')}) #{@throws && 'throws Exception ' || ''}{
		#{'return "";' if @ret != 'void'}
	}

EOT
	end
end

meths = Hash.new

ID = /\w[\w\d_]*/
VALUE = /[^\s]+/
FUNCALL = /(#{ID})((?:\s+(#{ID})=(?:#{VALUE})?)*)/
STRING_LITERAL = /".*?"/
EXPECT_ERROR = /expectError\s+#{STRING_LITERAL}\s+/
EXPECT_VALUE = /expect\s+(?:#{STRING_LITERAL}|#{VALUE})\s+/
EXPECT = /(?:#{EXPECT_ERROR}|#{EXPECT_VALUE})/
ASSIGN = /#{ID}=/
INSTR = /(#{EXPECT}|#{ASSIGN})?#{FUNCALL}/

instr = ''
line_number = 0
Dir.glob('../test/easyaccept/us-*.txt').each do |filename|
	file = File.new(filename, 'r')
	file.each_line do |line|
		line.chomp!
		line_number += 1
		next if line =~ /^#/ || line.strip.empty? || line =~ /^expectDifferent/

		instr += line

		if instr[-1,1] == '\\'
			instr.chop!
			next
		end

		m = Meth.new
		if instr =~ /^\s*expectError\s+#{STRING_LITERAL}\s+/
			m.throws = true
			instr = instr[$~.end(0)..-1]
		elsif instr =~ /^\s*expect\s+(#{STRING_LITERAL}|#{VALUE})\s+/
			m.ret = 'String'
			instr = instr[$~.end(0)..-1]
		elsif instr =~ /^\s*(expectDifferent|expectWithin)\s+/
			instr = instr[$~.end(0)..-1]
		elsif instr =~ /^\s*#{ID}=/
			m.ret = 'String'
			instr = instr[$~.end(0)..-1]
		end

		name = /^\s*#{ID}\s*/.match(instr)[0].strip
		instr = instr[$~.end(0)..-1]

		method = meths[name] || meths[name] = Meth.new(name)
    basename = File.basename(filename)
		method.lines[basename] ||= []
		method.lines[basename] << line_number
		if m.throws then method.throws = m.throws end
		if m.ret != 'void' then method.ret = m.ret end

		if method.parameters.empty?
			instr.scan(/(#{ID})=#{VALUE}/) do |pat|
				method.parameters << "String #{$~[1]}"
			end
		end

		instr = ''
	end
	file.close
end

file = File.new('BlmsFacade.java', 'w')
file.puts 'package blms.facade;'
file.puts
file.puts 'public class BlmsFacade {'
meths.keys.sort.each do |key|
	method = meths[key]
	file.puts method
end
file.puts '}'
file.puts
file.close
