package hello.core.member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 직접 저장소를 명시하지 않도록 한다
     * 객체를 생성하고 생성자를 통해 전달받음
     * from AppConfig
     */

    private final MemberRepository memberRepository;
    
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
/**
 * @Autowired -> Component Scan 후에 의존관계를 알아서 주입해준다
 * 얼마나 편하냐
 */

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Test 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
