package org.zerock.nado;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.nado.entity.Nado;
import org.zerock.nado.entity.QNado;
import org.zerock.nado.repository.NadoRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class NadoApplicationTests {

	@Autowired
	private NadoRepository nadoRepository;

	@Test
	public void insertDummies(){
		IntStream.rangeClosed(1,300).forEach(i -> {
			Nado nado = Nado.builder()
					.title("Title...." + i)
					.content("Content...." + i)
					.writer("user" + (i % 10))
					.build();
			System.out.println(nadoRepository.save(nado));
		});
	}

	@Test
	public void updateTest(){
		Optional<Nado> result = nadoRepository.findById(592L); // 존재하는 번호로 테스트

		if(result.isPresent()){
			Nado nado = result.get();

			nado.changeTitle("사우디아라비아");
			nado.changeContent("Changed Content...");

			nadoRepository.save(nado);
		}
	}

	@Test
	public void testQuery1(){
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

		QNado qNado = QNado.nado; //1
		String keyword = "1";
		BooleanBuilder builder = new BooleanBuilder(); // 2
		BooleanExpression expression = qNado.title.contains(keyword); // 3
		builder.and(expression); // 4
		Page<Nado> result = nadoRepository.findAll(builder, pageable); // 5
		result.stream().forEach(nado -> {
			System.out.println(nado);
		});
	}

	@Test
	public void testQuery2() {
		Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

		QNado qNado = QNado.nado;

		String keyword = "1";

		BooleanBuilder builder = new BooleanBuilder();

		BooleanExpression exTitle = qNado.title.contains(keyword);

		BooleanExpression exContent = qNado.content.contains(keyword);

		BooleanExpression exAll = exTitle.or(exContent); // 1--------------------------
		builder.and(exAll); // 2-----------
		builder.and(qNado.gno.gt(0L)); // 3------------
		Page<Nado> result = nadoRepository.findAll(builder,pageable);

		result.stream().forEach(nado -> {
			System.out.println(nado);
		});
	}
}
